import * as React from 'react';

import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import axios from 'axios';
import { useContext } from 'react';
import { LoginInfoContext } from '../Index';


export default function MediaCard(props) {

  const { loginInfo} = useContext(LoginInfoContext);


  const onDelete = ( e , bno, mno_fk ) =>{
    console.log("onDelete()");
    console.log( bno );

    if(mno_fk != loginInfo.mno){
      alert('본인 게시물이 아닙니다.!! 삭제할수 없습니다.!!');
      return;
    }
    axios.delete('/board/delete.do',{ params : {bno : bno }})
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

  return (
    <Card sx={{ maxWidth: 400 }} style={{margin : 10}}>
      <CardMedia
        sx={{ height: 200 }}
        image={"/uploadimg/"+props.board.bimgList[0]}
        title="green iguana"
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {props.board.bcontent}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small" onClick={ ( e ) => onDelete( e , props.board.bno, props.board.mno_fk ) }>삭제</Button>
        <Button size="small"></Button>
      </CardActions>
    </Card>
  );
}