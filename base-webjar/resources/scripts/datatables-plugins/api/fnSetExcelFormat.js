//custom button for cashflow excel generation
$.fn.dataTable.ext.buttons.excelCashFlow = {
    extend: 'excel', filename: 'cashFlow', sheetName: 'cashflow1', title: '',
    customize: function( xlsx ) {
// see built in styles here: https://datatables.net/reference/button/excelHtml5
// take a look at "buttons.html5.js", search for "xl/styles.xml"
//styleSheet.childNodes[0].childNodes[0] ==> number formats  <numFmts count="6"> </numFmts>
//styleSheet.childNodes[0].childNodes[1] ==> fonts           <fonts count="5" x14ac:knownFonts="1"> </fonts>
//styleSheet.childNodes[0].childNodes[2] ==> fills           <fills count="6"> </fills>
//styleSheet.childNodes[0].childNodes[3] ==> borders         <borders count="2"> </borders>
//styleSheet.childNodes[0].childNodes[4] ==> cell style xfs  <cellStyleXfs count="1"> </cellStyleXfs>
//styleSheet.childNodes[0].childNodes[5] ==> cell xfs        <cellXfs count="67"> </cellXfs>
//on the last line we have the 67 currently built in styles (0 - 66), see link above
 
        var sSh = xlsx.xl['styles.xml'];
        var lastXfIndex = $('cellXfs xf', sSh).length - 1;
 
        var n1 = '<numFmt formatCode="##0.0000%" numFmtId="300"/>';
        var s1 = '<xf numFmtId="300" fontId="0" fillId="0" borderId="0" applyFont="1" applyFill="1" applyBorder="1" xfId="0" applyNumberFormat="1"/>';
        var s2 = '<xf numFmtId="0" fontId="2" fillId="2" borderId="0" applyFont="1" applyFill="1" applyBorder="1" xfId="0" applyAlignment="1"><alignment horizontal="center"/></xf>';
//s3 is a combination of built in fonts 64 (2 dec places which has numFmtId="4") AND 2 (bold)
//just copied the xf of "two decimal places" and and changed the fontId based on "bold" 
        var s3 = '<xf numFmtId="4" fontId="2" fillId="0" borderId="0" applyFont="1" applyFill="1" applyBorder="1" xfId="0" applyNumberFormat="1"/>'
        sSh.childNodes[0].childNodes[0].innerHTML += n1;
        sSh.childNodes[0].childNodes[5].innerHTML += s1 + s2 + s3;
 
        var fourDecPlaces = lastXfIndex + 1;
        var greyBoldCentered = lastXfIndex + 2;
        var twoDecPlacesBold = lastXfIndex + 3;
 
        var sheet = xlsx.xl.worksheets['sheet1.xml'];
 
//two decimal places columns         
        var twoDecPlacesCols = ['F', 'I', 'J', 'K', 'L', 'M', 'N'];           
        for ( i=0; i < twoDecPlacesCols.length; i++ ) {
            $('row c[r^='+twoDecPlacesCols[i]+']', sheet).attr( 's', '64' );
        }
        $('row c[r^="G"]', sheet).attr( 's', fourDecPlaces );  //% 4 decimal places, as added above
//                $('row c', sheet).attr( 's', '25' ); //for all rows
        $('row:eq(0) c', sheet).attr( 's', greyBoldCentered );  //grey background bold and centered, as added above
        $('row:eq(1) c', sheet).attr( 's', '7' );  //grey background bold
        $('row:last c', sheet).attr( 's', '2' );  //bold
//row with totals  
        var boldCols = ['A', 'C', 'D', 'E'];        
        for ( i=0; i < boldCols.length; i++ ) {
            $('row:eq(-2) c[r^='+boldCols[i]+']', sheet).attr( 's', '2' );  //bold
        }
//move text from column B to column A and empty columns B through E
        var copyPaste = $('row:eq(-2) c[r^="B"] t', sheet).text();
        $('row:eq(-2) c[r^="A"] t', sheet).text(copyPaste);
        var emptyCellCols = ['B', 'C', 'D', 'E'];
        for ( i=0; i < emptyCellCols.length; i++ ) {
            $('row:eq(-2) c[r^='+emptyCellCols[i]+']', sheet).text('');
        }
 
        var twoDecPlacesBoldCols = ['I', 'J', 'K', 'M', 'N'];  
        for ( i=0; i < twoDecPlacesBoldCols.length; i++ ) {
            $('row:eq(-2) c[r^='+twoDecPlacesBoldCols[i]+']', sheet).attr( 's', twoDecPlacesBold );
        }      
    },
    exportOptions: {
//            columns: ':visible',
        format: {
            body: function ( data, row, column, node ) {
                // Strip $ from salary column to make it numeric
                if (typeof data !== 'undefined') {
                    if (data != null) { 
                        if ( column > 13 ) {
                            return '';  //get rid of the changed manually column
                        }
                        if (column === 5 || column === 6 || column > 7) {
                           /* if (lang == 'de') { //this time we use the English formatting
                                //data contain only one comma we need to split there
                                var arr = data.split(',');
                                //subsequently replace all the periods with spaces
                                arr[0] = arr[0].toString().replace( /[\.]/g, "" );
                                //join the pieces together with a period if not empty
                                if (arr[0] > ''  || arr[1] > '') {
                                    data = arr[0] + '.' + arr[1];
                                } else {
                                    return '';
                                }
                            } else {*/
                                data = data.toString().replace( /[\,]/g, "" );
                            //}
                            //result a number still as a string with decimal . and
                            //no thousand separators
                            //replace everything except numbers, decimal point and minus
                            data = data.toString().replace( /[^\d.-]/g, "" );
                            //percent must be adjusted to fraction to work ok
                            if (column == 6) {
                                if (data !== '') {
                                    data = data / 100;
                                }
                            }
                            return data;                               
                        }
                    }
                }
                return data;
            },
            header: function ( data, column ) {
                if (typeof data !== 'undefined') {
                    if (data != null) {
                        if ( column > 13 ) {
                            return '';  //get rid of the changed manually column
                        }
                    }
                }
                return data;
            }
        }
    }
};