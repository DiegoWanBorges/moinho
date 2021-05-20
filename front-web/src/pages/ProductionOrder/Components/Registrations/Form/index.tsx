import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import './styles.scss';

function ProductionOrderForm() {

    return (
        <form >

            <Tabs className="tab-main">
                <TabList>
                    <Tab>Ingredientes</Tab>
                    <Tab>Title 2</Tab>
                    <Tab>Title 3</Tab>
                </TabList>

                <TabPanel>
                    <h2>Any content 1</h2>
                </TabPanel>

            </Tabs>
        </form>
    );
}
export default ProductionOrderForm;