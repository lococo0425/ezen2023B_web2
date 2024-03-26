package ezenweb.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //===============================단방향=============================//
        //1. 회원가입
        회원 m1 = 회원.builder()
                .회원번호(1)
                .아이디("qwe")
                .이름("유재석")
                .build();

        //2. '유재석'이 게시물작성
        게시물 b1 = 게시물.builder()
                .번호(1).게시물제목("제목").게시물내용("내용").작성자(m1)
                .build();

        //3. 게시물 작성한 회원 정보 호출 [게시물을 통해 작성자를 알수있다.]
        System.out.println("b1.get작성자() = " + b1.get작성자());

        //===============================양방향=============================//
        m1.get내가쓴글().add(b1);
        System.out.println("m1.get내가쓴글() = " + m1.get내가쓴글());

        //4. 유재석이 게시물을 하나 더 작성 
        게시물 b2 = 게시물.builder().번호(2).게시물제목("제목2").게시물내용("내용2")
                .작성자(m1)
                .build();
        m1.get내가쓴글().add(b2);

        System.out.println("m1.get내가쓴글() = " + m1.get내가쓴글());
    }
}
