import Home from '../../pages/Home';
import { Route, Router, Switch, RouteProps, Redirect } from 'react-router-dom'
import NavBar from '../components/NavBar';
import history from '../utils/history';
import Login from '../../pages/Login'
import { isTokenValid } from 'core/utils/auth';
import PrivateRoute from './PrivateRoutes';
import Registration from 'pages/Registrations';


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
              
                <Redirect from="/registrations" to="/registrations/parameters" exact/>
                <PrivateRoute path="/registrations">
                    <Registration />
                </PrivateRoute>
            </Switch>
        </Router>
    )
}
export default Routes;