package com.youcode.citronix.utils;

public class Constants {
    // Farm constraints
    public static final int MAX_FIELDS_PER_FARM = 10;
    public static final double MIN_FARM_AREA = 1000.0; // 0.1 hectare in m²
    public static final double MAX_FIELD_AREA_PERCENTAGE = 0.5; // 50% of farm area
    public static final int TREES_PER_HECTARE = 100;

    // Tree productivity constraints (kg per season)
    public static final double YOUNG_TREE_PRODUCTION = 2.5;  // < 3 years
    public static final double MATURE_TREE_PRODUCTION = 12.0; // 3-10 years
    public static final double OLD_TREE_PRODUCTION = 20.0;   // > 10 years
    public static final int MAX_TREE_AGE = 20;

    // Season constraints
    public static final int PLANTING_SEASON_START_MONTH = 3;  // March
    public static final int PLANTING_SEASON_END_MONTH = 5;    // May
    public static final int HARVEST_SEASON_START_MONTH = 9;   // September
    public static final int HARVEST_SEASON_END_MONTH = 11;    // November
}
