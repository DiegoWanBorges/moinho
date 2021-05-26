import PrivateRoute from 'core/routes/PrivateRoutes'
import './styles.scss'

import { Switch } from 'react-router-dom'
import OperationalCostTypes from './Components/OperationalCostTypes'
import NavBarRegistration from './Components/NavBar'
import Parameters from './Components/Parameters'
import Users from './Components/Users'
import Groups from './Components/Groups'
import Units from './Components/Units'
import Product from './Components/Product'
import Sectors from './Components/Sectors'
import Employees from './Components/Employees'
import LaborCostTypes from './Components/LaborCosTypes'
import Occurrences from './Components/Occurrences'
import Producedproductstatus from './Components/PalletStatus'
import MainPage from 'core/components/MainPage'

const Registration = () => (
    <div className="admin-container">
        <NavBarRegistration />
        <div className="admin-content">
            <Switch>
                <PrivateRoute  path="/registrations/" exact={true}>
                    <MainPage />
                </PrivateRoute>
                <PrivateRoute path="/registrations/parameters" allowedRoutes={['ROLE_ADMIN', 'ROLE_REGISTRATION_PARAMETER']}>
                    <Parameters />
                </PrivateRoute>
                <PrivateRoute path="/registrations/users" allowedRoutes={['ROLE_ADMIN', 'ROLE_REGISTRATION_USER']}>
                    <Users />
                </PrivateRoute>
                <PrivateRoute path="/registrations/groups" allowedRoutes={['ROLE_ADMIN', 'ROLE_REGISTRATION_GROUP']}>
                    <Groups />
                </PrivateRoute>
                <PrivateRoute path="/registrations/units" allowedRoutes={['ROLE_ADMIN', 'ROLE_REGISTRATION_UNITY']}>
                    <Units />
                </PrivateRoute>
                <PrivateRoute path="/registrations/products" allowedRoutes={['ROLE_ADMIN', 'ROLE_REGISTRATION_PRODUCT']}>
                    <Product />
                </PrivateRoute>
                <PrivateRoute path="/registrations/sectors" allowedRoutes={['ROLE_ADMIN', 'ROLE_REGISTRATION_SECTOR']}>
                    <Sectors />
                </PrivateRoute>
                <PrivateRoute path="/registrations/employees" allowedRoutes={['ROLE_ADMIN', 'ROLE_REGISTRATION_EMPLOYEE']}>
                    <Employees />
                </PrivateRoute>
                <PrivateRoute path="/registrations/laborcosttypes" allowedRoutes={['ROLE_ADMIN', 'ROLE_REGISTRATION_LABORCOSTTYPE']}>
                    <LaborCostTypes />
                </PrivateRoute>
                <PrivateRoute path="/registrations/operationalcosttypes" allowedRoutes={['ROLE_ADMIN', 'ROLE_REGISTRATION_OPERATIONALCOSTTYPE']}>
                    <OperationalCostTypes />
                </PrivateRoute>
                <PrivateRoute path="/registrations/occurrences" allowedRoutes={['ROLE_ADMIN', 'ROLE_REGISTRATION_OCCURRENCE']}>
                    <Occurrences />
                </PrivateRoute>
                <PrivateRoute path="/registrations/palletstatus" allowedRoutes={['ROLE_ADMIN', 'ROLE_REGISTRATION_STATUSPALLET']}>
                    <Producedproductstatus />
                </PrivateRoute>
            </Switch>
        </div>
    </div>
);

export default Registration;
