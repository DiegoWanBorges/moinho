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
    const [startDate, setStartDate] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth(), 1, 0, 0));
    const [endDate, setEndDate] = useState(new Date(new Date(Date.now()).getFullYear(), new Date(Date.now()).getMonth() + 1, 0, 23, 59));
    const [productionOrderResponse, setProductionOrderResponse] = useState<ProductionOrdersResponse>();
    const [activePage, setActivePage] = useState(0);

    const getProductionOrders = useCallback(() => {
        const params = {
            page: activePage,
            startDate: moment(startDate).format("DD/MM/YYYY HH:mm"),
            endDate: moment(endDate).format("DD/MM/YYYY HH:mm"),
            linesPerPage: 10,
            orderBy: "id",
            direction: "DESC"
        }
        console.log(params)
        makePrivateRequest({ url: '/productionorders', params })
            .then(response => setProductionOrderResponse(response.data))
            .finally(() => {

            })
    }, [activePage, startDate, endDate])

    useEffect(() => {
        getProductionOrders();
    }, [activePage, getProductionOrders])

    const history = useHistory();

    const onRemove = (productionOrderId: number) => {
        const confirm = window.confirm("Deseja excluir a ordem de produção selecionada?");
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
                .catch((error) => {
                    toast.error(error.response.data.message)
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
                        onChange={(e) => setStartDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={startDate}
                    />

                </div>

                <div className="production-list-filter-end-date">
                    <label className="label-base">Dt. Final:</label>
                    <DateTime
                        dateFormat="DD/MM/YYYY"
                        timeFormat="HH:mm"
                        onChange={(e) => setEndDate(moment(e.toString()).toDate())}
                        closeOnSelect={true}
                        locale="pt-br"
                        initialValue={endDate}
                    />
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