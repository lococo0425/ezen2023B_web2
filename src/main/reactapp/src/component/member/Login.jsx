import axios from "axios";

export default function Login(){

    const onLogin = () =>{
        //폼가져오기
        const loginForm = document.querySelector('#loginForm');
        //데이터 폼으로 변환
        const loginFormData = new FormData(loginForm);
        //통신
        axios.post('/member/login/post.do',loginFormData)
            .then((r)=>{console.log(r)
                if(r.data){
                    alert('로그인성공')
                    window.location.href="/"
                }else{
                    alert('로그인실패')
                }
            })
            .catch((e)=>{console.log(e);})
    }

    return(<>
        <form id="loginForm">
            이메일 : <input type="text" name="memail"/><br/>
            비밀번호 : <input type="password" name="mpassword"/><br/>
            <button type="button" onClick={onLogin}>로그인</button>

        </form>
    </>)
}