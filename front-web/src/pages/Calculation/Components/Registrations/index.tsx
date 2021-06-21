import { Route, Switch } from 'react-router';
import CostCalculationCreate from './Create';
import CalculationList from './List'
import CostCalculationForm from './Form'

import './styles.scss';

function CalculationRegistration() {
    return (
        <div>
            <Switch>
                <Route path="/calculations/registrations" exact>
                    <CalculationList />
                </Route>
                <Route path="/calculations/registrations/create" exact>
                    <CostCalculationCreate/>
                </Route>
                <Route path="/calculations/registrations/:costCalculationId">
                    <CostCalculationForm />
                </Route>
            </Switch>
        </div>
    );
}
export default CalculationRegistration;