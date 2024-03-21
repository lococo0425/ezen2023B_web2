import React from "react";
import Comment from "./Comment";

export default function CommentList(props){
 
    //서버
    let response = [ {name : '유재석' , content : '안녕하세요1'} ,
    {name : '강호동' , content : '안녕하세요2'} ,
    {name : '신동엽' , content : '안녕하세요3'} ];
    return (
        <div>
            {
                response.map((data)=>{
                    return(
                        <Comment name = {data.name} comment = {data.content}/>                
                        )
                })
            }
        </div>
    );
}