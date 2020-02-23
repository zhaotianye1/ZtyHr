package org.zty.ztyspringboothr;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class ZtyspringboothrApplicationTests {

    @Test
    void contextLoads() {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123"));
    }

    @Test
    public void test(){
        String i="|T|ES|T";
        int j=i.split("|").length;
        String zz[]=i.split("|");
        for(String z : zz){
        System.out.println(j+"aaa"+z+z.length());
        }
        String i1="T";
        String i2="ES";
        int j1=i1.length();
        int j2=i2.length();
        System.out.print(j1+"haha"+j2);
    }
    @Test
    public void test1(){
        System.out.print("5"+2);
    }



}
