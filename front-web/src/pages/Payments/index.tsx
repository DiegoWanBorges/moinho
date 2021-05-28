import MainPage from 'core/components/MainPage'
import PrivateRoute from 'core/routes/PrivateRoutes'
import { Switch } from 'react-router'
import NavBarPayments from './Components/NavBar'

import './styles.scss'
const Payment = () => {
    return (
        <div className="admin-container">
            <NavBarPayments />
            <div className="admin-content">
                <Switch>
                    <PrivateRoute path="/payments/" exact={true}>
                        <MainPage/>
                    </PrivateRoute>
                    
                    <PrivateRoute path="/payments/employees" >
                        
                    </PrivateRoute>
                </Switch>
            </div>
        </div>
    )

}
export default Payment
