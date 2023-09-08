package com.green.campingsmore.admin.main.model;

import lombok.Data;

import java.util.List;

@Data
public class SelAggregateFinalVO {
    private List<SelAggregateVO> statistics;
    private SevenDaysTotalSum sevenSum;
    private SevenDaysTotalAverage sevenAverage;
}
