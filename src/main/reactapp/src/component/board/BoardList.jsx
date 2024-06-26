import axios from "axios"
import { useEffect, useState } from "react";
import MediaCard from "./MediaCard";
import { Pagination } from "@mui/material";

export default function BaordList( props ){
    // 1. useState 변수
    const [ pageDto , setPageDto ] = useState( {
        page : 1 , count : 0, data : [ ]
    } );

    // 2.
    useEffect( ()=>{
        const info = {page : pageDto.page , view : 4}
        axios.get( '/board/get.do', { params : info } )
            .then( response => { console.log(response)
                // 컴포넌트 렌더링된 이후 axios가 데이터를 가져왔어.
                // 그럼 컴포넌트 다시 렌더링(새로고침) 해야 되는데..
                // 서버로 받은 데이터를 setState 넣어주면 재렌더링
                setPageDto( response.data );
            })
            .catch( error => { console.log( error );  })
    } , [pageDto.page ] )

    const handleChange = (e, value) => {
        pageDto.page=value;
        setPageDto({...pageDto});
    }
    return (<>
    <div style={{display:"flex"}}>
        {
            pageDto.data.map( ( board ) => {
                return (
                    <MediaCard board = {board}/>
                )
            })
        }
    </div>    
    <ul>
        <Pagination count={pageDto.count} page={pageDto.page} onChange={handleChange} />
    </ul>

    </>)
}