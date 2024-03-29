import axios from "axios";

export default function Logout(props){

    axios.get("/member/logout/get.do")
    .then((r)=>{console.log(r)
        if(r){
            alert('로그아웃 성공')
            window.location.href="/"
        }else{
            alert('로그아웃 실패')
        }
    })
    .catch((e)=>{console.log(e)})

    return(<>
    
    </>)

}