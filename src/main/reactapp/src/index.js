import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

// * 내가 만든 컴포넌트 import(가져오기) 호출
// import 컴포넌트명from 컴포넌트파일경로;
import JSX선언 from './chapter3/1_JSX선언';

// chapter3 실습
import Book from './chapter3/Book';
import Library from './chapter3/Library';

// chapter4 실습
import Clock from './chapter3/Clock';

// chapter5 실습
import CommentList from './chapter5/CommentList';

// chapter7 예제/실습
import Counter from './chapter7/Counter'
import UseStateList from './chapter7/UseStateList';


// chapter13 예제/실습
import ProfileCard from './chapter13/ProfileCard';

// chapter14 예제/실습
import DarkOrLight from './chapter14/DarkOrLigth'

// chapter0 axios
import Axios컴포넌트 from './chapter0/Axios컴포넌트';
// chapter0 Route
import Route컴포넌트 from './chapter0/Route컴포넌트';

// web2 라우터 컴포넌트
import Index from './component/Index';

const root = ReactDOM.createRoot(document.querySelector('#root'));
root.render(
    // <CommentList/> // <Counter />  // <UseStateList />  // <ConfirmButton />
    // <LandingPage />  // <Counter2 /> // <AttendenceBook />  // <NameForm />  // <SignUp />
    // <Axios컴포넌트 />   // <Route컴포넌트 />
    // <Calculator /> // <ProfileCard />
    // <DarkOrLight />
    <Index />
);
// !!!!!!!여기가 렌더링 되는 곳이에요
// root.render(
//   <React.StrictMode>
//     {/* <App /> */}
//     {/* <JSX선언 /> */}
//     {/* <Book /> */}
//     {/* <Library /> */}
//     <Clock />
//   </React.StrictMode>
// );

// 실습4 setInterval( 함수() , 밀리초 ) : 밀리초 마다 해당 함수 실행
// setInterval(() => {
//   root.render( <Clock /> );
// }, 1000);


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();