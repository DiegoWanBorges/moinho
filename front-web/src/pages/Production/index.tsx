import PrivateRoute from 'core/routes/PrivateRoutes'
import './styles.scss'
import { Switch } from 'react-router-dom'
import NavBarProduction from './Components/NavBar';
import ProductionList from './Components/Registrations/List';
const Production = () => ( 
    <div className="admin-container">
        <NavBarProduction/>
        <div className="admin-content">
            <Switch>
                <PrivateRoute path="/productions/registrations" >
                    <ProductionList/>
                </PrivateRoute>
                <PrivateRoute path="/productions/reports" >
                    <h1>Relatorios</h1>
                </PrivateRoute>
            </Switch>
        </div>
    </div>
);

export default Production;
