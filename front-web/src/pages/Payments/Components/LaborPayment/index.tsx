import { Route, Switch } from 'react-router';
import './styles.scss'


const LaborPayments = () => {
    return (
        <div>
            <Switch>
                <Route path="/payments/labor" exact>
                    <h1>labor list</h1>
                </Route>
                <Route path="/payments/labor/:laborPaymentId">
                    <h1>labor form</h1>
                </Route>
            </Switch>
        </div>
    )
}
export default LaborPayments;