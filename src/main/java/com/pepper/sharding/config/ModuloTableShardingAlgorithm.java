package com.pepper.sharding.config;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 分表策略，根据order_id分
 */
public class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Long> {

    /**
     * 对order_id取模，为0放在t_order_0，为1放在t_order_1
     * @param tableNames
     * @param shardingValue
     * @return
     */
    @Override
    public String doEqualSharding(Collection<String> tableNames, ShardingValue<Long> shardingValue) {
        for (String each : tableNames) {
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Collection<String> doInSharding(Collection<String> tableNames, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        for (Long value : shardingValue.getValues()) {
            for (String tableName : tableNames) {
                if (tableName.endsWith(value % 2 + "")) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> tableNames, ShardingValue<Long> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        Range<Long> range = (Range<Long>) shardingValue.getValueRange();
        for (Long i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : tableNames) {
                if (each.endsWith(i % 2 + "")) {
                    result.add(each);
                }
            }
        }
        return result;
    }
}

