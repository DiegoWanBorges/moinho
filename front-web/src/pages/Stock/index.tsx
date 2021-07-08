import PrivateRoute from 'core/routes/PrivateRoutes'
import { Switch } from 'react-router'
import NavBarStockMovemente from './Components/NavBar'
import Stockmovements from './Components/Movements'
import './styles.scss'

const StockMovement = () => {
    return (
        <div className="admin-container">
            <NavBarStockMovemente />
            <div className="admin-content">
                <Switch>
                    <PrivateRoute path="/stock/movements" allowedRoutes={["ROLE_ADMIN","ROLE_STOCK"]}>
                        <Stockmovements/>
                    </PrivateRoute>
                </Switch>
            </div>
        </div>
    )

}
export default StockMovement
