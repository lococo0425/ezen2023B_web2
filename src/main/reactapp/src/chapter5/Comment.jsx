import React from "react";

//CSS 파일 호출 : import styles from 'css파일경로'
import styles from './Comment.css'
// 이미지 파일 호출 : 
import image from './image.PNG'


export default function Comment(props){
    console.log("props : ");
    console.log(props);
    return (
        <div className="wrapper">
            <div>
                <img src={image} className="image"/>
            </div>
            <div className="contentContainer">
                <span className="nameText">
                    {props.name}
                </span>
                <span className="commentText">{props.comment}</span>
            </div>

        </div>
    )
}