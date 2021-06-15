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
                <PrivateRoute path="/calculations/registrations" >
                    <CalculationRegistration/>
                </PrivateRoute>
                <PrivateRoute path="/calculations/reports" >
                    <h1>Relatorios</h1>
                </PrivateRoute>
            </Switch>
        </div>
    </div>
);

export default Calculation;