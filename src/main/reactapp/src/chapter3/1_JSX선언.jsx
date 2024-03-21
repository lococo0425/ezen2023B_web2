
import React from "react";

let user = {name : '강호동', age : 10} //JS객체(전역)

function formatName(user){      //JS 함수
    return user.name + ' ' + user.age
}



function JSX선언(props){

    let name = '유재석'; //변수



    return (
    <div>
        안녕하세요 리엑트 공간.<br/> 
        저는 {name} 입니다.<br/>
        {formatName(user)}
        
    </div>
    );

}
export default JSX선언;