// 컨텍스트를 이용한 컴포넌트 트리로 곧바로 전달하기 위한 컨텍스트 저장소 생성
const ThemeContext = React.createContext('light');



//1 컴포넌트 1 : 최상위 컴포넌트
// Provider를 사용 하여 하위 컴포넌트들에게 현재 데이터 전달 
// 모든 하위 컴포넌트들은  컴포넌트 트리 하단에 얼마나 깊이 있는지에 관계없이 데이터 호출 
export default function ContextApp2(props){

    return(<>
        <ThemeContext.Provider value="dark">
            <Toolbar/>
        </ThemeContext.Provider>
    </>)
}
//2 컴포넌트 2 : 중간 컴포넌트
//중간에 위치한 컴포넌트는 테마 데이터를 하위 컴포넌트로 전달할 필요가 없다.

function Toolbar(props){ //props = {theme : dark}
    console.log(props)
    return(<>
        <div>
            <ThemedButton />
        </div>
    </>)
}
//3 컴포넌트 3 : 최하위 첨포넌트
//Consumer를 사용하여 리엑트는 가장 가까운 상위 테마 provider를 찾아서 해당되는 값을 호출 

function ThemedButton(props){

    return(<>
    <ThemeContext.Consumer>
       {value => <Button theme={value} />}
    </ThemeContext.Consumer>
    </>)
}

