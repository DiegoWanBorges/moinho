import { Route, Switch } from 'react-router';
import SectorForm from './Form';
import SectorList from './List';

import './styles.scss';

function Sectors() {
    return (
        <div>
            <Switch>
                <Route path="/registrations/sectors" exact>
                    <SectorList/>
                </Route>
                <Route path="/registrations/sectors/:sectorId">
                    <SectorForm/>
                </Route>
            </Switch>
        </div>
    );
}
export default Sectors;
