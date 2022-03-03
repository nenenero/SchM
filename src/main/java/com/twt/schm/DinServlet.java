package com.twt.schm;

import com.twt.schm.entity.Food;
import com.twt.schm.mapper.FoodMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet("/din")
public class DinServlet extends HttpServlet {
    SqlSessionFactory factory;
    @SneakyThrows
    @Override
    public void init() throws ServletException {

        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DinServlet DoGet被调用");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DinServlet DoPost被调用");
        resp.setContentType("text/html;charset=UTF-8");
        Map<String,String[]> map = req.getParameterMap();
        if(map.containsKey("din")){
           int din = Integer.parseInt(req.getParameter("din"));
            try(SqlSession sqlSession = factory.openSession(true)){
                FoodMapper foodMapper = sqlSession.getMapper(FoodMapper.class);
                ArrayList<Food> foods = foodMapper.getFoodDin(din);
                for(int i=0;i<3;i++){
                    if (foods!=null&&foods.size()>0){
                        resp.getWriter().write(foods.get(i).getF_name()+"\n");
                        resp.getWriter().write("食堂： 学"+foods.get(i).getF_din()+"食堂\n");
                        resp.getWriter().write("价格： "+foods.get(i).getF_price()+"\n");
                    }else{
                        System.out.println("food is null");
                    }
                }
            }
        }else{
            System.out.println("非法输入");
            resp.getWriter().write("请正确输入食堂号");
        }
    }
}

