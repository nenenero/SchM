package com.twt.schm.mapper;

import com.twt.schm.entity.Food;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface FoodMapper {




    @Select("select * from schmfood order by rand() limit 3")
    ArrayList<Food> getFoodRand();
    @Select("select * from schmfood where f_din=#{f_din} order by rand() limit 3")
    ArrayList<Food> getFoodDin(int din);
    @Select("select * from schmfood where f_din=#{f_din} and f_price between #{f_price1} and #{f_price2} and f_type=#{f_type} order by rand() limit 3")
    ArrayList<Food> getFoodCom(@Param("f_din")String din,@Param("f_price1")int price1,@Param("f_price2")int price2,@Param("f_type")String type);
    @Select("select * from schmfood where f_din=#{f_din} and f_price between #{f_price1} and #{f_price2} order by rand() limit 3")
    ArrayList<Food> getFoodComExc(@Param("f_din")String din,@Param("f_price1")int price1,@Param("f_price2")int price2);
    @Select("select * from schmfood where f_id=#{f_id}")
    Food getFoodId(int id);
    @Update("update schmfood set f_count=f_count+1 where f_id=#{f_id}")
    void setCount(int id);
}

