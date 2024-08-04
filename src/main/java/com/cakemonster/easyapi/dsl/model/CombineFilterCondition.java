package com.cakemonster.easyapi.dsl.model;

import java.util.List;

/**
 * CombineFilterCondition
 *
 * @author cakemonster
 * @date 2024/8/4
 */
public class CombineFilterCondition implements FilterCondition {

    private SymbolEnum symbol;

    private List<FilterCondition> conditions;
}
