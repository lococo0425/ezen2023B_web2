import { axios } from "axios";
import { useRef, useState } from "react";

export default function Chatting(props){
    //1. 해당 컴포넌트가 렌더링 될때 소켓은 재랜더링 방지 ==> useRef 사용
        //useRef(초기값) :          {current : 값}
        // 컴포넌트가 렌더링시 참조값을 고정 
    let clientSocket = useRef(null);
    //2. Ref 참조가 없으면 
    if(!clientSocket.current){

        //(클라이언트) 웹 소켓 구현 
        // 1. new WebSocket(서버소켓url); //비동기
        clientSocket.current = new WebSocket("ws://192.168.17.14:80/chat");
            // 확인
        console.log(clientSocket); // 확인
        // onclose // onerror // onmessage // onopen : webSocket객체내 포함된 메소드들
            //2. 각 메소드 정의 
            // -1. 클라이언트 소켓이 close 되었을때 콜백함수 정의 
        clientSocket.current.onclose = (e) => {console.log(e); console.log('소켓 닫힘');}
            // -2. 클라이언트 소켓에 error 발생했을때 콜백함수 정의 ( * error 은 이미 발생 했고 이 이후 행동 정의) 
        clientSocket.current.onerror = (e) => {console.log(e); console.log('소켓 에러');}
            // -3. 클라이언트 소켓이 message 받았을때 콜백 함수 정의 
        clientSocket.current.onmessage = (e) => {console.log(e); console.log('소켓 메시지 받음'); alert('서버에게 메시지 도착'); console.log(e.data);
            msgList.push(e.data);
            setMsgList([...msgList]);

        }
            // -4 클라이언트 소켓이 open이 발생했을때 콜백 함수 정의 
        clientSocket.current.onopen = (e) =>{console.log(e); console.log('소켓 열림');}
    }


    
    //4. 연결 종료
    //clientSocket.close();


    //=======================================================

    const onSend = () =>{
            // 2. 연결된 서버소켓 에게 메시지 보내기
    clientSocket.current.send(msgInput);
    }
    //- 채팅 내용 입력창 
    const [ msgInput, setMsgInput] = useState('');
    // - 채팅 창의 내용물 들
    const [ msgList, setMsgList] = useState([]);
    return(<>
        <div>
            <h3>채팅방</h3>
            <div>
                {
                    msgList.map((msg)=>{
                        return <div>{msg}</div>
                    })
                }
            </div>
            <textarea value={msgInput} onChange={(e) => {setMsgInput(e.target.value)}}></textarea>
            <button type="button" onClick={onSend}>전송</button>
        </div>
    </>)
}