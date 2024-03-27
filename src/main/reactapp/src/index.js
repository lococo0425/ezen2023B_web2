import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

// 내가 만든 컴포넌트 import(가져오기) 호출
// import 컴포넌트명 from 컴포넌트파일경로;
import JSX선언 from './chapter3/1_JSX선언';
//chapter0 실습
import Axios컴포넌트 from './chapter0/Axios컴포넌트';
import Route컴포넌트 from './chapter0/Route컴포넌트';

// chapter3 실습
import Book from './chapter3/Book';
import Library from './chapter3/Library';
import Clock from './chapter3/Clock';
// chapter5 실습
import CommentList from './chapter5/CommentList';
// chapter7 실습
import Counter from './chapter7/Counter';
import UseStateList from './chapter7/UseStateList';
// chapter 11 실습


import SignUp from './component/member/SignUp';

//web2 라우터 컴포넌트
import Index from './component/Index';


const root = ReactDOM.createRoot(document.getElementById('root'));
 
// root.render( // 여기가 랜더링 되는 곳 입니다.!!!!!!!!!!!!!
//   <React.StrictMode>
//     {/* <JSX선언 /> */}
//     {/* <Book /> */}
//     {/* <App/> */}
//     {/* <Library/> */}
//     <Clock/>
//     {/* <CommentList/> */}
//      <Counter/>
//   </React.StrictMode>
// );
// setInterval(()=>{
//   root.render(<Clock/>)
// },1000)

root.render(
  <React.StrictMode>
    {/* <UseStateList/> */}
    {/* <SignUp/> */}
    {/* <Axios컴포넌트/> */}
    {/* <Route컴포넌트/> */}
    <Index/>
  </React.StrictMode>
)




reportWebVitals();
