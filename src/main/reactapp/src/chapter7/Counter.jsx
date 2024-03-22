import { useState } from "react";

export default function Counter(props){
    // 1. 변수
    let countValue = 0;
    // 2. 함수
    function 클릭함수(){
         count++;
        console.log('함수 안 : ' +countValue);
    }
    console.log('컴포넌트 안 : '+countValue);

    // 3. 상태관리변수 !!! : state 값의 변화(set~~~)가 있으면 해당 컴포넌트 재호출/랜더링
        //1. import {useState} from "react";
        //2. useState(초기값)
    let 상태관리변수 = useState('훅이란무엇인가?')
    console.log(상태관리변수);
    console.log(상태관리변수[0]);
    let [count, setCount]= useState(0)
    let [inputValue1, setInputValue1] = useState('')
    function inputValue1Update(e){
        setInputValue1(e.target.value)
    }
    
    return (<>
        {/* 일반 변수 사용 */}
        <div>
            <p>총 {countValue} 번 클릭 했습니다.</p>
            <button onClick={()=> countValue++} type="button">클릭</button>
        </div>
        {/* state 변수 활용 */}
        <div>
            <p>총 {count} 번 클릭 했습니다.</p>
            <button onClick={()=> setCount(count+1)} type="button">클릭</button>
        </div>

        <div>
            <input type="text"/>
            <input type="text" value={inputValue1}/>
            <input type="text" value={inputValue1} onChange={inputValue1Update}/>
        </div>

        </>);
}
