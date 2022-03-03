package com.twt.schm;

import com.twt.schm.entity.Food;
import com.twt.schm.mapper.FoodMapper;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
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


//通过菜品的id进行查询，并输出详细信息
@WebServlet("/id")
public class IdServlet extends HttpServlet{
    SqlSessionFactory factory;
    @SneakyThrows
    @Override
    public void init() throws ServletException {

        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id=0;
        id = Integer.parseInt(req.getParameter("id"));
        resp.setContentType("text/html;charset=UTF-8");
        try(
                SqlSession sqlSession = factory.openSession(true)){
            FoodMapper foodMapper = sqlSession.getMapper(FoodMapper.class);
            if(id!=0){
            Food food = foodMapper.getFoodId(id);
            foodMapper.setCount(id);
                if (food!=null){

                    if(food.getF_win()!=null){
                        resp.getWriter().write("窗口："+food.getF_win()+"\n");
                    }else{
                        resp.getWriter().write("窗口： "+"--"+"\n");
                    }
                    resp.getWriter().write("备注："+food.getF_others()+"\n");
                    resp.getWriter().write("热度（被选择次数，仅供参考）："+food.getF_count()+"\n");

                }else{
                    System.out.println("food is null");
                }
            }

        }
    }

}
