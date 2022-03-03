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


@WebServlet(value = "/rand",loadOnStartup = 1)
public class RandServlet extends HttpServlet {
    SqlSessionFactory factory;
    int din=0;
    ArrayList<Food> food;
    @SneakyThrows
    @Override
    public void init() throws ServletException {
        System.out.println("RandServlet init被调用");
        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RandServlet DoGet被调用");
        try(SqlSession sqlSession = factory.openSession(true)){
            FoodMapper foodMapper = sqlSession.getMapper(FoodMapper.class);
            resp.setContentType("text/html;charset=UTF-8");
            din = Integer.parseInt(req.getParameter("din"));
            if(din==0) {
                food = foodMapper.getFoodRand();
            }else {
                food = foodMapper.getFoodDin(din);
            }
            if (food!=null&&food.size()>0){
                for(int i=0;i<3;i++){
                    resp.getWriter().write(food.get(i).getF_id()+"\n");
                    resp.getWriter().write(food.get(i).getF_name()+"\n");
                    resp.getWriter().write("食堂： 学"+food.get(i).getF_din()+"食堂\n");
                    if(food.get(i).getF_price()!=null){
                        resp.getWriter().write("价格： "+food.get(i).getF_price()+"\n");
                    }else{
                        resp.getWriter().write("价格： "+"--"+"\n");
                    }
                }
            }else{
                System.out.println("food is null");
            }
        }
    }





    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("dopost被调用");
    }
}
