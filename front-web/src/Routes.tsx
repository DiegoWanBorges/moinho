import Home from './pages/Home';
import { Route, Router, Switch,RouteProps, Redirect  } from 'react-router-dom'
import NavBar from './core/components/NavBar';
import history from './core/utils/history';
import Login from './pages/Login'
import {  isTokenValid } from 'core/utils/auth';


const Routes = () => {
   
    function ProtectedRoute({...routerProps}: RouteProps) {
        if(isTokenValid()) {
          return <Route {...routerProps} />;
        } else {
          return <Redirect to={{ pathname: "/" }} />;
        }
      };
    
    return (
        <Router history={history}>
            <NavBar />
            <Switch>
                <Route  path="/" exact>
                    <Login />
                </Route>
                <ProtectedRoute path='/home' exact> 
                    <Home/> 
                </ProtectedRoute>
            </Switch>
        </Router>
    )
}
export default Routes;