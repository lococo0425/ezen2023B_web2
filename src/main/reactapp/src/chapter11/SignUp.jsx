import React, { useState } from "react";

function SignUp(props){
    const [ name, setName ] = useState("");

    const handleChangeName = (event)=>{
        setName(event.target.value);

    };

    const handelSubmit = ( event )=>{
        alert(`이름 : ${name}`);
        event.preventDefault();
    }
    return (
        <form onSubmit={handelSubmit}>
            <label>
                이름:
                <input type="text" value={name} onChange={handleChangeName} />
            </label>
            <button type="submit">제출</button>
        </form>
    );
}

export default SignUp;