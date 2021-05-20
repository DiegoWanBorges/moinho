import './styles.scss';
import { useCallback, useEffect, useState } from 'react';
import DateTime from 'react-datetime'
import moment from 'moment';
import 'moment/locale/pt-br';
import { ProductionOrdersResponse } from 'core/types/ProductionOrder';
import { makePrivateRequest } from 'core/utils/request';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import Pagination from 'core/components/Pagination';
import ProductionOrderCard from '../Card';


function ProductionOrderList() {
    const [initialDate, setInitialDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [productionOrderResponse, setProductionOrderResponse] = useState<ProductionOrdersResponse>();
    const [activePage, setActivePage] = useState(0);
    
  
    const getProductionOrders = useCallback(() => {
        console.log(initialDate)
        console.log(endDate)
        const params = {
            page: activePage,
            startDate: moment(initialDate).format("DD/MM/YYYY HH:mm"),
            endDate:moment(endDate).format("DD/MM/YYYY HH:mm"),
            linesPerPage: 10,
            orderBy:"id",
            direction:"DESC"
        }
        console.log(params)
        makePrivateRequest({ url: '/productionorders', params })
            .then(response => setProductionOrderResponse(response.data))
            .finally(() => {
    
            })
    }, [activePage,initialDate,endDate])

    const onSearch = () => {
        getProductionOrders();
    }

    useEffect(() =>{
        getProductionOrders();
    },[activePage,getProductionOrders])
 
    const history = useHistory();

    const onRemove = (productionOrderId: number) => {
        const confirm = window.confirm("Deseja excluir o grupo selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/productionorders/${productionOrderId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Ordem de Produção cancelada com sucesso!")
                    history.push('/productions/registrations')
                    getProductionOrders();
                })
                .catch(() => {
                    toast.error("Falha ao cancelar order de produção!")
                    history.push('/productions/registrations')
                })
        }
    }
    const handCreate = () => {
        history.push("/productions/registrations/create");
    }
    return (
        <div className="production-list">
            <div className="production-list-actions">
            <button
                className="btn btn-primary btn-lg production-list-btn-add"
                onClick={handCreate}
            >
                Nova O.P.
            </button>
            </div>

            <div className="production-list-filter">
                <div className="production-list-filter-initial-date">
                    <label className="label-base">Dt. Inicial:</label>
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat="HH:mm"
                        onChange={(e) => setInitialDate(e.toString())}
                        closeOnSelect={true}
                        locale="pt-br"
                    />

                </div>

                <div className="production-list-filter-end-date">
                    <label className="label-base">Dt. Final:</label>
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat="HH:mm"
                        onChange={(e) => setEndDate(e.toString())}
                        closeOnSelect={true}
                        locale="pt-br"
                    />
                </div>
                <div className="production-list-filter-btn">
                    <button 
                        className="btn btn-success"
                        onClick={onSearch}
                    >
                        Pesquisar
                    </button>
                </div>

            </div>

            
            <div className="admin-list-container">
                {productionOrderResponse?.content.map(productionOrder => (
                    <ProductionOrderCard
                        productionOrder={productionOrder} key={productionOrder.id}
                        onRemove={onRemove}
                    />
                ))}

                {productionOrderResponse &&
                    <Pagination
                        totalPages={productionOrderResponse?.totalPages}
                        onChange={page => setActivePage(page)}
                    />
                }
            </div>
        </div>
    );
}
export default ProductionOrderList;