import PrivateRoute from 'core/routes/PrivateRoutes'
import './styles.scss'

import { Switch } from 'react-router-dom'
import NavBarFormulation from './Components/NavBar';
import FormulationRegistration from './Components/Registrations';


const Formulation = () => (
    <div className="admin-container">
        <NavBarFormulation />
        <div className="admin-content">
            <Switch>
                <PrivateRoute path="/formulations/registrations" >
                    <FormulationRegistration/>
                </PrivateRoute>
                <PrivateRoute path="/formulations/reports" >
                    <h1>Relatorios</h1>
                </PrivateRoute>
            </Switch>
        </div>
    </div>
);

export default Formulation;
