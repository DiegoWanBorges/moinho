import PrivateRoute from 'core/routes/PrivateRoutes'
import './styles.scss'

import { Switch } from 'react-router-dom'
import NavBarCalculation from './Components/NavBar';
import CalculationRegistration from './Components/Registrations';



const Calculation = () => ( 
    <div className="admin-container">
        <NavBarCalculation />
        <div className="admin-content">
            <Switch>
                <PrivateRoute path="/calculations/registrations" allowedRoutes={["ROLE_ADMIN","ROLE_COST_CALCULATION"]}>
                    <CalculationRegistration/>
                </PrivateRoute>
            </Switch>
        </div>
    </div>
);

export default Calculation;