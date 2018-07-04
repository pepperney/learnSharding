package com.pepper.sharding.config;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 分库策略--根据user_id分库
 */
public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm<Long> {

    /**
     * 对user_id取模，为0放在database0，为1放在database1
     * @param databaseNames
     * @param shardingValue
     * @return
     */
    @Override
    public String doEqualSharding(Collection<String> databaseNames, ShardingValue<Long> shardingValue) {
        for (String each : databaseNames) {
            if (each.endsWith(Long.parseLong(shardingValue.getValue().toString()) % 2 + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doInSharding(Collection<String> databaseNames, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(databaseNames.size());
        for (Long value : shardingValue.getValues()) {
            for (String tableName : databaseNames) {
                if (tableName.endsWith(value % 2 + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> databaseNames, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(databaseNames.size());
        Range<Long> range = (Range<Long>) shardingValue.getValueRange();
        for (Long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : databaseNames) {
                if (each.endsWith(i % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}
