import { Route, Router, Switch } from 'react-router-dom'
import NavBar from './core/components/NavBar';
import history from './core/utils/history';
import Login from './pages/Login'


const Routes = () => {
    return (
        <Router history={history}>
            <NavBar/>
            <Switch>
            <Route path="/" exact>
                <Login/>
            </Route>
        </Switch>
        </Router>
        
    )
}

export default Routes;