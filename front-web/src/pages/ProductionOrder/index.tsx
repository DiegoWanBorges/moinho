import PrivateRoute from 'core/routes/PrivateRoutes'
import './styles.scss'
import { Switch } from 'react-router-dom'
import NavBarProduction from './Components/NavBar';
import ProductionOrderRegistration from './Components/Registrations/';
const Production = () => ( 
    <div className="admin-container">
        <NavBarProduction/>
        <div className="admin-content">
            <Switch>
                <PrivateRoute path="/productions/registrations" allowedRoutes={["ROLE_ADMIN","ROLE_PRODUCTION"]}>
                    <ProductionOrderRegistration/>
                </PrivateRoute>
            </Switch>
        </div>
    </div>
);

export default Production;
