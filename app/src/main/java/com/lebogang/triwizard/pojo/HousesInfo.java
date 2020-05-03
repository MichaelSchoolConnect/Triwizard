package com.lebogang.triwizard.pojo;

    /**
     * This is really not a POJO(Plain Old Java Object) class. It only holds reference to the data
     * we'll later bind in the adapter.
     *
     * I later realized that we can re-use this class for referencing data to the 'HouseIdActivity' too,
     * reducing boilerplate code.
     * */

    public class HousesInfo {

        /**
         *HousesInfoActivity Strings.
         **/

        //Reference to the first value
        public String id;
        public String values_0;
        public String values_1;
        public String values_2;
        public String values_3;
    }

