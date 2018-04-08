package com.example.cheshta.nirmalhindan.adapter;

import java.util.ArrayList;

/**
 * Created by tushm on 07-04-2018.
 */

public class YojnaAdapter {
    String name;
    String Description;

    public YojnaAdapter(String name, String description) {
        this.name = name;
        Description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public static ArrayList<YojnaAdapter> genRandCourses (int num) {
        ArrayList<YojnaAdapter> courses = new ArrayList<>(num);


        courses.add(new YojnaAdapter(
                " Water Conservation",
                "Water conservation includes all the policies, strategies and activities to sustainably manage the natural resource of fresh water, to protect the hydrosphere, and to meet the current and FutureEventActivity human demand. Population, household size, and growth and affluence all affect how much water is used. Factors such as climate change have increased pressures on natural water resources especially in manufacturing and agricultural irrigation.[1] Many US cities have already implemented policies aimed at water conservation, with much success."

        ));
        courses.add(new YojnaAdapter(
                "Plantation",
                "A plantation is a large-scale farm that specializes in cash crops. The crops grown include cotton, coffee, tea, cocoa, sugar cane, sisal, oil seeds, oil palms, rubber trees, and fruits. Protectionist policies and natural comparative advantage have sometimes contributed to determining where plantations were located."

               +" A plantation house is the main house of a plantation, often a substantial farmhouse, which often serves as a symbol for the plantation as a whole."
        ));
        courses.add(new YojnaAdapter(
                " Chemical Free Agriculture",
                "Organic farming is an alternative agricultural system which originated early in the 20th century in reaction to rapidly changing farming practices. Organic farming continues to be developed by various organic agriculture organizations today. It relies on fertilizers of organic origin such as compost manure, green manure, and bone meal and places emphasis on techniques such as crop rotation and companion planting. Biological pest control, mixed cropping and the fostering of insect predators are encouraged. In general, organic standards are designed to allow the use of naturally occurring substances while prohibiting or strictly limiting synthetic substances.[2] For instance, naturally occurring pesticides such as pyrethrin and rotenone are permitted, while synthetic fertilizers and pesticides are generally prohibited. "
        ));
        courses.add(new YojnaAdapter(
                "Liquid waste",
                "Liquid waste can be defined as such Liquids as wastewater, fats, oils or grease (FOG), used oil, liquids, solids, gases, or sludges and hazardous household liquids. These liquids that are hazardous or potentially harmful to human health or the environment."

        ));
        courses.add(new YojnaAdapter(
                "Public Partnership",
                "Public Partnerships is a financial management service (FMS) organization that is dedicated to assisting state, county, and local public agencies to implement a consumer-directed service model allowing participants to make individual choices about which services they receive, how they are delivered"
        ));


        return  courses;
    }
}
