import { axios } from "axios";
import { useContext, useEffect, useRef, useState } from "react";
import { LoginInfoContext } from "../Index";
import styles from './Chatting.css';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';

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
            msgList.push(JSON.parse(e.data));
            setMsgList([...msgList]);

        }
            // -4 클라이언트 소켓이 open이 발생했을때 콜백 함수 정의 
        clientSocket.current.onopen = (e) =>{console.log(e); console.log('소켓 열림');}
    }


    
    //4. 연결 종료
    //clientSocket.close();


    //=======================================================
    const {loginInfo} = useContext( LoginInfoContext );


    const onSend = () =>{
    
        let info = {
            msg : msgInput,                 // 작성된 내용
            fromMemail : loginInfo.memail,   //현재 로그인 작성자
            type : 'msg',
            hour : new Date().getHours(),
            min : new Date().getMinutes()
        }
    
    
        // 2. 연결된 서버소켓 에게 메시지 보내기
    clientSocket.current.send(JSON.stringify(info));

    }
    //- 채팅 내용 입력창 
    const [ msgInput, setMsgInput] = useState('');
    // - 채팅 창의 내용물 들
    const [ msgList, setMsgList] = useState([]);
    // - 채팅 내용 입력창에서 엔터키를 이용한 전송
    const activeEnter = (e) => {

        if(e.keyCode == 13 && e.ctrlKey){
            setMsgInput(msgInput + '\n'); return;
        }
        if(e.keyCode == 13){ // 엔터를 눌렀을때
            onSend();
            return;

        }
    }
    useEffect(()=>{
        let chatcont = document.querySelector('.chatcont')
        console.log(chatcont.scroll)
        console.log(chatcont.scrolltop)     //현재 스크롤의 상단 위치 
        console.log(chatcont.scrollHeight)  //스크롤 전체 높이 길이
        
        chatcont.scrollTop = chatcont.scrollHeight; //상단 위치를 최하단 위치로 변경

    })
    // 이모티콘 이미지를 클릭했을때 전송
    const onEmoSend = (emo) =>{
        let info = {
            msg : emo,
            fromMemail : loginInfo.memail,
            type : 'emo',
            hour : new Date().getHours(),
            min : new Date().getMinutes()
            
        }
        clientSocket.current.send(JSON.stringify(info))
        //- 드롭다운 닫기 
        handleClose();
    }


    //드랍다운 api
    const [anchorEl, setAnchorEl] = useState(null);
    const open = Boolean(anchorEl);
    const handleClick = (event) => {
      setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
      setAnchorEl(null);
    };
  
  
    //- msg 타입에 따른 HTML 반환 함수 
    const typeHTML = (m) =>{
        if(m.type=='msg'){
            return <div className="content">{m.msg}</div>
        }else if(m.type == 'emo'){
            return <img src ={'/emo/'+m.msg}/>
         }
    }



    return (<>
        <h3> 채팅방 </h3>
        <div className="chatbox">
            <div className="chatcont">

                {
                    msgList.map( (m)=>{
                        return (<>
                            {
                                loginInfo.memail == m.fromMemail ? 
                                    (
                                        <div className="rcont">
                                            <div className="subcont">
                                                <div className="date"> {m.hour} : {m.min} </div>
                                                {typeHTML(m)}
                                            </div>
                                        </div>
                                    ) :
                                    <div className="lcont">
                                        <img className="pimg" src={'/uploadimg/짜앙구.jpg'} />
                                        <div className="tocont">
                                            <div className="name">{ m.fromMemail } </div>
                                            <div className="subcont">
                                                {typeHTML(m)}
                                                <div className="date"> {m.hour} : {m.min}  </div>
                                            </div>
                                        </div>
                                    </div>
                            }       
                        </>);
                    })
                }
            </div>
            <div className="chatbottom">
                <textarea value={msgInput} onChange= { (e)=>{ setMsgInput( e.target.value) }} onKeyDown={ activeEnter}> </textarea>
                <button type="button" onClick={ onSend }> 전송 </button>
            </div>
            <div>
                <Button
                    id="basic-button"
                    aria-controls={open ? 'basic-menu' : undefined}
                    aria-haspopup="true"
                    aria-expanded={open ? 'true' : undefined}
                    onClick={handleClick}
                >
                    이모티콘
                </Button>
                <Menu
                    id="basic-menu"
                    anchorEl={anchorEl}
                    open={open}
                    onClose={handleClose}
                    MenuListProps={{
                    'aria-labelledby': 'basic-button',
                    }}
                >   
                    <div style={{height:200, width : 500, overflowY : 'scroll'}}>
                    {
                        Array(43).fill().map((v,i)=>{
                            return (
                            <>
                            <img src={`/emo/emo${i+1}.gif`} onClick={(e) => onEmoSend(`emo${i+1}.gif`)}/>
                            {(i+1) % 5 == 0 && <br/>}
                            </>
                        )
                        })
                    }
                    </div>

                </Menu>
                </div>
        </div>
    </>)
}