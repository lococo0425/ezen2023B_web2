import axios from "axios";


export default function onDelete(boardId){
    const onDelete = () =>{
        console.log("onDelete()");

        axios.delete('/board/delete.do',{data : {bno : boardId}})
            .then(r=>{console.log(r)
            if(r.data){
                alert('삭제성공');
                window.location.href="/board"
            }else{
                alert('삭제실패 관리자에게 문의하세요')
            }
            })
            .catch(e=>{console.log(e)})
    }

    return(<></>)
}