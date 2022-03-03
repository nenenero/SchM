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

//复合查询
@WebServlet("/com")
public class ComServlet extends HttpServlet {
    SqlSessionFactory factory;
    @SneakyThrows
    @Override
    public void init() throws ServletException {
        System.out.println("ComServlet init被调用");
        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ComServlet DoGet被调用");
        int price1 = 0;
        int price2 = 100;
        String type="";
        String din = "";
        ArrayList<Food> food;
        try(SqlSession sqlSession = factory.openSession(true)){
            FoodMapper foodMapper = sqlSession.getMapper(FoodMapper.class);
            resp.setContentType("text/html;charset=UTF-8");

            if(req.getParameter("din")!=null){
                din = req.getParameter("din");
            }
            if(req.getParameter("price")!=null){
                switch (Integer.parseInt(req.getParameter("price"))){
                    case 0:{price1=0;
                        price2=100;
                        break;}
                    case 1:{price1 = 0;
                        price2=5;
                        break;}
                    case 2:{price1=5;
                        price2=10;
                        break;}
                    case 3:{price1=10;
                        price2=100;
                        break; }
                }
            }
            if(req.getParameter("type")!=null){
                type = req.getParameter("type");

                if(type.equals("0")){
                    food = foodMapper.getFoodComExc(din,price1,price2);
                }else {
                    food = foodMapper.getFoodCom(din,price1,price2,type);}
            }else {
                food = foodMapper.getFoodComExc(din,price1,price2);
            }
            if(food!=null&&food.size()>0){
                for(int i = 0;i<3;i++){
                    resp.getWriter().write(food.get(i).getF_id()+"\n");
                    resp.getWriter().write(food.get(i).getF_name()+"\n");
                    resp.getWriter().write("食堂： 学"+food.get(i).getF_din()+"食堂\n");
                    if(food.get(i).getF_price()!=null){
                        resp.getWriter().write("价格： "+food.get(i).getF_price()+"\n");
                    }else{
                        resp.getWriter().write("价格： "+"--"+"\n");
                    }
                }
            }
        }
    }





    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("dopost被调用");
    }
}
