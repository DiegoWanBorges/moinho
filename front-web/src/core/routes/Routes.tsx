import Home from '../../pages/Home';
import { Route, Router, Switch, RouteProps, Redirect } from 'react-router-dom'
import NavBar from '../components/NavBar';
import history from '../utils/history';
import Login from '../../pages/Login'
import { isTokenValid } from 'core/utils/auth';
import PrivateRoute from './PrivateRoutes';
import Registration from 'pages/Registrations';
import Formulation from 'pages/Formulations';
import Production from 'pages/ProductionOrder';


const Routes = () => {

    function ProtectedRoute({ ...routerProps }: RouteProps) {
        if (isTokenValid()) {
            return <Route {...routerProps} />;
        } else {
            return <Redirect to={{ pathname: "/" }} />;
        }
    };

    return (
        <Router history={history}>
            <NavBar />
            <Switch>
                <Route path="/" exact>
                    <Login />
                </Route>
                <ProtectedRoute path='/home' exact>
                    <Home />
                </ProtectedRoute>
              
                <PrivateRoute path="/registrations">
                    <Registration />
                </PrivateRoute>
                
                <Redirect from="/formulations" to="/formulations/registrations" exact/>
                <PrivateRoute path="/formulations">
                    <Formulation />
                </PrivateRoute>
                <Redirect from="/productions" to="/productions/registrations" exact/>
                <PrivateRoute path="/productions">
                    <Production />
                </PrivateRoute>
            </Switch>
        </Router>
    )
}
export default Routes;