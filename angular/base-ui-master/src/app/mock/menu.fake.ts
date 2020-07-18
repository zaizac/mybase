export const MENU_FAKE_DATA: any = [
  {
    menuCode: 'CMN_COMPONENTS',
    menuParentCode: 'ROOT',
    menuDesc: { en: 'Components' },
    menuLevel: 1,
    menuSequence: 1,
    menuIcon: 'mdi mdi-bullseye',
    menuUrlCd: 'two-column',
    menuChild: [
      {
        menuCode: 'CMN_TABLES',
        menuParentCode: 'ROOT',
        menuDesc: { en: 'Datatable' },
        menuLevel: 1,
        menuSequence: 1,
        menuChild: [
          {
            menuCode: 'CMN_TABLES_DEFAULT',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Basic' },
            menuLevel: 3,
            menuSequence: 1,
            menuUrlCd: '/components/datatable/basic'
          },
          {
            menuCode: 'CMN_TABLES_CONFIG',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Configuration' },
            menuLevel: 3,
            menuSequence: 2,
            menuUrlCd: '/components/datatable/configuration'
          },
          {
            menuCode: 'CMN_TABLES_CONFIG',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Column Rendering' },
            menuLevel: 3,
            menuSequence: 3,
            menuUrlCd: '/components/datatable/render'
          },
          {
            menuCode: 'CMN_TABLES_CONFIG',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Search and Reset' },
            menuLevel: 3,
            menuSequence: 5,
            menuUrlCd: '/components/datatable/searchandreset'
          },
          {
            menuCode: 'CMN_TABLES_CONFIG',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Checkbox and Radio Button' },
            menuLevel: 3,
            menuSequence: 4,
            menuUrlCd: '/components/datatable/checkboxandradio'
          },
          {
            menuCode: 'CMN_TABLES_CONFIG',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Templating' },
            menuLevel: 3,
            menuSequence: 6,
            menuUrlCd: '/components/datatable/templating'
          },
          {
            menuCode: 'CMN_TABLES_CONFIG',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Events' },
            menuLevel: 3,
            menuSequence: 7,
            menuUrlCd: '/components/datatable/events'
          },
          {
            menuCode: 'CMN_TABLES_CONFIG',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Expandable Row' },
            menuLevel: 3,
            menuSequence: 8,
            menuUrlCd: '/components/datatable/expandablerow'
          },
          {
            menuCode: 'CMN_TABLES_CONFIG',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Card' },
            menuLevel: 3,
            menuSequence: 9,
            menuUrlCd: '/components/datatable/card'
          }
        ]
      },
      {
        menuCode: 'CMN_FILE_UPLOAD',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'File Upload' },
        menuLevel: 2,
        menuSequence: 1,
        menuUrlCd: '/components/fileupload'
      },
      {
        menuCode: 'CMN_SELECT',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'Select' },
        menuLevel: 2,
        menuSequence: 1,
        menuChild: [
          {
            menuCode: 'CMN_SELECT_DEFAULT',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Basic' },
            menuLevel: 3,
            menuSequence: 1,
            menuUrlCd: '/components/select/basic'
          },
          {
            menuCode: 'CMN_SELECT_MULTI',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Multiselect' },
            menuLevel: 3,
            menuSequence: 2,
            menuUrlCd: '/components/select/multi'
          }
        ]
      },
      {
        menuCode: 'CMN_BUTTONS',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'Buttons' },
        menuLevel: 2,
        menuSequence: 1,
        menuUrlCd: '/components/buttons'
      },
      {
        menuCode: 'CMN_DATE_TIME_PICKER',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'Date & Time Picker' },
        menuLevel: 2,
        menuSequence: 1,
        menuUrlCd: '/components/datepicker',
        menuChild: [
          {
            menuCode: 'CMN_DATE',
            menuParentCode: 'CMN_DATE_TIME_PICKER',
            menuDesc: { en: 'Date Picker' },
            menuLevel: 3,
            menuSequence: 1,
            menuUrlCd: '/components/picker/date'
          },
          {
            menuCode: 'CMN_DATE_RANGE',
            menuParentCode: 'CMN_DATE_TIME_PICKER',
            menuDesc: { en: 'Date Range Picker' },
            menuLevel: 3,
            menuSequence: 2,
            menuUrlCd: '/components/picker/daterange'
          },
          {
            menuCode: 'CMN_DATETIME',
            menuParentCode: 'CMN_DATE_TIME_PICKER',
            menuDesc: { en: 'Date & Time Picker' },
            menuLevel: 3,
            menuSequence: 5,
            menuUrlCd: '/components/picker/datetime'
          },
          {
            menuCode: 'CMN_DATETIME_RANGE',
            menuParentCode: 'CMN_DATE_TIME_PICKER',
            menuDesc: { en: 'Date & Time Range Picker' },
            menuLevel: 3,
            menuSequence: 6,
            menuUrlCd: '/components/picker/datetimerange'
          },
          {
            menuCode: 'CMN_DATE_CUSTOM_RANGE',
            menuParentCode: 'CMN_DATE_TIME_PICKER',
            menuDesc: { en: 'Custom Date Range Picker' },
            menuLevel: 3,
            menuSequence: 7,
            menuUrlCd: '/components/picker/daterangecustom'
          },
          {
            menuCode: 'CMN_TIME',
            menuParentCode: 'CMN_DATE_TIME_PICKER',
            menuDesc: { en: 'Time Picker' },
            menuLevel: 3,
            menuSequence: 3,
            menuUrlCd: '/components/picker/time'
          },
          {
            menuCode: 'CMN_Time_RANGE',
            menuParentCode: 'CMN_DATE_TIME_PICKER',
            menuDesc: { en: 'Time Range Picker' },
            menuLevel: 3,
            menuSequence: 4,
            menuUrlCd: '/components/picker/timerange'
          }
        ]
      },
      {
        menuCode: 'CMN_RATING',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'Ratings' },
        menuLevel: 2,
        menuSequence: 1,
        menuUrlCd: '/components/rating'
      },
      {
        menuCode: 'CMN_ACCORDION',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'Accordion' },
        menuLevel: 2,
        menuSequence: 1,
        menuUrlCd: '/components/accordion'
      },
      {
        menuCode: 'CMN_TABS',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'Tabs' },
        menuLevel: 2,
        menuSequence: 1,
        menuUrlCd: '/components/tabs'
      },
      {
        menuCode: 'CMN_ALERT',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'Alerts & Notifications' },
        menuLevel: 2,
        menuSequence: 1,
        menuUrlCd: '/components/alert'
      },
      {
        menuCode: 'CMN_CALENDAR',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'Calendar' },
        menuLevel: 2,
        menuSequence: 1,
        menuUrlCd: '/components/calendar'
      },
      {
        menuCode: 'CMN_OTHERS',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'Others' },
        menuLevel: 2,
        menuSequence: 1,
        menuUrlCd: '/components/others'
      },
      {
        menuCode: 'CMN_SPINNER',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'Spinner' },
        menuLevel: 2,
        menuSequence: 1,
        menuUrlCd: '/components/spinner'
      },
      {
        menuCode: 'CMN_DUALLISTBOX',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'Dual List Box' },
        menuLevel: 2,
        menuSequence: 1,
        menuUrlCd: '/components/duallist'
      },
      {
        menuCode: 'CMN_QRCODE',
        menuParentCode: 'CMN_COMPONENTS',
        menuDesc: { en: 'QRCode' },
        menuLevel: 2,
        menuSequence: 1,
        menuUrlCd: '/components/qrcode'
      }
    ]
  },
  {
    menuCode: 'CMN_COMPONENTS',
    menuParentCode: 'ROOT',
    menuDesc: { en: 'Extra' },
    menuLevel: 1,
    menuSequence: 2,
    menuUrlCd: '',
    menuIcon: 'mdi mdi-widgets',
    menuChild: [
      {
        menuCode: 'CMN_FORM',
        menuParentCode: 'ROOT',
        menuDesc: { en: 'Forms' },
        menuLevel: 2,
        menuSequence: 1,
        menuChild: [
          {
            menuCode: 'CMN_FORM_BASIC',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Basic Forms' },
            menuLevel: 3,
            menuSequence: 1,
            menuUrlCd: '/form/basic'
          },
          {
            menuCode: 'CMN_FORM_BASIC',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Form Validation' },
            menuLevel: 3,
            menuSequence: 2,
            menuUrlCd: '/form/validation'
          }
        ]
      },
      {
        menuCode: 'CMN_WIZARD',
        menuParentCode: 'ROOT',
        menuDesc: { en: 'Form Wizard' },
        menuLevel: 2,
        menuSequence: 2,
        menuChild: [
          {
            menuCode: 'CMN_FORM_WIZARD',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Step By Step Guide' },
            menuLevel: 3,
            menuSequence: 1,
            menuUrlCd: '/form/wizard/guide'
          },
          {
            menuCode: 'CMN_FORM_WIZARD',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Horizontal Example' },
            menuLevel: 3,
            menuSequence: 2,
            menuUrlCd: '/form/wizard/horizontal'
          },
          {
            menuCode: 'CMN_FORM_WIZARD',
            menuParentCode: 'ROOT',
            menuDesc: { en: 'Vertical Example' },
            menuLevel: 3,
            menuSequence: 3,
            menuUrlCd: '/form/wizard/vertical'
          }
        ]
      }
    ]
  }
];
