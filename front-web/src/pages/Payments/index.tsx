import './styles.scss'

import PrivateRoute from 'core/routes/PrivateRoutes'
import { Switch } from 'react-router'
import LaborPayments from './Components/LaborPayment'
import NavBarPayments from './Components/NavBar'
import OperationalPayments from './Components/Operational'
import MainPage from 'core/components/MainPage'

const Payment = () => {
    return (
        <div className="admin-container">
            <NavBarPayments />
            <div className="admin-content">
                <Switch>
                    <PrivateRoute path="/payments/" exact={true}>
                        <MainPage />
                    </PrivateRoute>
                    <PrivateRoute path="/payments/labor" allowedRoutes={["ROLE_ADMIN","ROLE_LABOR_PAYMENT"]}>
                        <LaborPayments />
                    </PrivateRoute>
                    <PrivateRoute path="/payments/operational" allowedRoutes={["ROLE_ADMIN","ROLE_OPERATIONAL_PAYMENT"]}>
                        <OperationalPayments />
                    </PrivateRoute>
                </Switch>
            </div>
        </div>
    )

}
export default Payment
