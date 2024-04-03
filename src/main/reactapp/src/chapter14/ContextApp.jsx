//1 컴포넌트 1 : 최상위 컴포넌트
export default function ContextApp(props){

    return(<>
        <Toolbar theme = "dark"/>
    </>)
}
//2 컴포넌트 2 : 중간 컴포넌트
function Toolbar(props){ //props = {theme : dark}
    console.log(props)
    return(<>
        <div>
            <ThemedButton theme={props.theme}/>
        </div>
    </>)
}
//3 컴포넌트 3 : 최하위 첨포넌트
function ThemedButton(props){

    return(<>
        <Button theme={props.theme}/>;
    </>)
}