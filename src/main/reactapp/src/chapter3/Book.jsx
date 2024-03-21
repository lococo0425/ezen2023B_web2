/*
    컴포넌트 만드는 방법 
        1. 첫글자를 대문자로 하는 .jsx 파일 생성한다. 
        2. 함수형 컴포넌트 생성
            2-1 컴포넌트 함수 선언
                function 컴포넌트명 (props){
                    return(JSX 형식문법);
                }
            2-2 다른 곳에서 해당 파일 import시 반환할 컴포넌트 명시 
                export default 해당파일호출시반환컴포넌트명
             
            2-1 + 2-2 
                export default function Book(props){
                    return (JSX 형식문법) ;
                }
    컴포넌트를 호출하는 방법
        1. 다른 컴포넌트에서 해당 컴포넌트 호출하는 방법
        import 컴포넌트명 from 컴포넌트파일경로;

*/

import React from "react";

function Book(props){

    return(
        <div>
            <h1>{props.name}</h1>
            <h2>나이 : {props.age}</h2>
        </div>
    );
}
export default Book

