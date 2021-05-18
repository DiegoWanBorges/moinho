import './styles.scss';
import { useState } from 'react';
import DateTime from 'react-datetime'
import moment from 'moment';
import 'moment/locale/pt-br';

function ProductionList() {
    const [initialDate, setInitialDate] = useState('');
    const [endDate, setEndDate] = useState('');



    function verData() {
        console.log(moment(initialDate).format("DD/MM/YYYY HH:mm"));
        console.log(moment(endDate).format("DD/MM/YYYY HH:mm"));
    }

    return (
        <div className="production-list">
            <div className="production-list-actions">
                <button
                    className="btn btn-primary btn-lg production-list-btn-add"
                    onClick={verData}
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

            </div>


        </div>
    );
}
export default ProductionList;