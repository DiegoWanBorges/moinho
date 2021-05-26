import { Route, Switch } from 'react-router';
import ProducedProductStatusForm from './Form';
import ProducedProductStatusList from './List';


import './styles.scss';

function ProducedProductStatus() {
    return (
        <div>
            <Switch>
                <Route path="/registrations/palletstatus" exact>
                    <ProducedProductStatusList/>
                </Route>
                <Route path="/registrations/palletstatus/:producedProductStatusId">
                    <ProducedProductStatusForm/>
                </Route>
            </Switch>
        </div>
    );
}
export default ProducedProductStatus;
