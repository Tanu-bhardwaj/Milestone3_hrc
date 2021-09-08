Ext.application({
    name: 'Fiddle',

    launch: function () {
        //write your code here
        var filterPanel = Ext.create('Ext.panel.Panel', {
            bodyPadding: 5, 
            title: '<div style="text-align:center;">Movie Advance Search</div>',
            renderTo: Ext.getBody(),
            layout: 'hbox',
            border: 0,
            items: [{
                xtype: 'textfield',
                labelWidth: 80,
                
                fieldLabel: 'Movie_Name',
                
                margin: '5 25 0 500'

            }, {
                xtype: 'textfield',

                fieldLabel: 'Director_Name',
                margin: '5 0 0 0'

            }]

        });
        var filterPanel_2 = Ext.create('Ext.panel.Panel', {
            bodyPadding: 5, 

            renderTo: Ext.getBody(),
            layout: 'hbox',
            border: 0,
            items: [{
                    xtype: 'datefield',
                    
                    labelWidth: 80,
                    
                    fieldLabel: 'Relase_year',
                    margin: '5 20 0 500'

                   
                }, {
                   
                    xtype: 'combo',
                    margin: '5 28 0 6',
                    fieldLabel: 'Language:',
                    
                    emptyText: ' select',

                    store: new Ext.data.SimpleStore({

                        
                        data: [
                            [0, '1'],
                            [1, '2'],
                            [2, '3'],
							[3, '4'],
                        ],
                        id: 0,
                        fields: ['value', 'text']
                    }),
                    valueField: 'value',
                    displayField: 'text',

                    listeners: {
                        change: function (combo, newValue, oldValue) {

                        }
                    }
                }

            ]

        });
        var filterPanel_3 = Ext.create('Ext.panel.Panel', {
            bodyPadding: 5, // Don't want content to crunch against the borders

            renderTo: Ext.getBody(),
            layout: 'hbox',
            border: 0,
            items: [{
                xtype: 'button',
                text: 'Search',
                margin: '0 6 0 730'
            }, {
                xtype: 'button',
                text: 'Reset'
            }]

        });
        Ext.define('User', {
            extend: 'Ext.data.Model',
            fields: ['film_id', 'Title', 'description', 'director', 'language_id', 'year', 'rating', 'special_Features']
        });

        var userStore = Ext.create('Ext.data.Store', {
            model: 'User',

            pageSize: 10,
            proxy: {
                type: 'ajax',
                url: 'http://localhost:8080/Summer_Internship_Backend/getMovie.action',
                reader: {
                    type: 'json',
                    rootProperty: 'details',
					totalProperty:'totalRows',
                },
               // noCache: false
            },
            autoLoad: true,
        });
        userStore.load();
        Ext.create('Ext.grid.Panel', {
            renderTo: Ext.getBody(),
            store: userStore,
            id: 'Movie_grid',

            title: 'Movie grid',
            selModel: {
                type: 'checkboxmodel'
            },
            columns: [

                {
                    text: 'Title',

                    sortable: false,
                    hideable: false,
                    dataIndex: 'title',
                    flex: 1

                }, {
                    text: 'Description',

                    dataIndex: 'description',
                    flex: 1
                        // hidden: true
                }, {
                    text: 'Director',

                    dataIndex: 'director',
                    flex: 1
                        // hidden: true
                }, {
                    text: 'language_id',

                    dataIndex: 'language_id',
                    flex: 1
                        // hidden: true
                }, {
                    text: 'Release year',

                    dataIndex: 'year',
                    flex: 1
                        // hidden: true
                },

                {
                    text: 'Rating',

                    dataIndex: 'rating',
                    flex: 1
                        // hidden: true
                },

                {
                    text: 'Special_Features',
                    flex: 1,
                    dataIndex: 'special_Features',

                }
            ],
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'pagingtoolbar',
                    store: userStore, // same store GridPanel is using
                    displayInfo: false,
                    margin: '0 120 0 0',
                }, {
                    xtype: 'button',
					iconCls: 'x-fa fa-plus',
					text: 'Add',
					tooltip:'Add',
					
                    handler: function () {
                        var add_form = Ext.create('Ext.window.Window', {
                            title: 'Add Film',
                            height: 510,
                            width: 500,
                            items: {
                                xtype: 'form',
                                
                                items: [{
                                    xtype: 'textfield',
                                    fieldLabel: 'Movie Name',
                                    id: 'one',
                                    padding: 10,
                                    width: 470,
                                    //margin : '10 0 0 350',
                                    name: 'title',
                                    
                                }, {
                                    xtype: 'numberfield',
                                    fieldLabel: 'Release Year',
                                    padding: 10,
                                    id: 'two',
                                    width: 470,
                                    //margin: '10 0 0 100',
                                    name: 'releaseyear',
                                    
                                }, {
                                    xtype: 'combo',
                                    fieldLabel: 'Special Feature',
                                    store: ['Trailers', 'Commentaries', 'Deleted Scenes', 'Behind the Scenes'],
                                    width: 470,
                                    id: 'three',
                                    padding: 10,
                                   
                                    name: 'namefeature'
                                        
                                }, {
                                    xtype: 'combobox',
                                    id: 'four',
                                    fieldLabel: 'Rating',
                                    padding: 10,
                                    width: 470,
                                    store: ['G','PG','PG-13','R'], 
                                    
                                    name: 'ratings'
                                        
                                }, {
                                    xtype: 'combobox',
                                    fieldLabel: 'Language',
                                    id: 'five',
                                    padding: 10,
                                    width: 470,
                                    store: ['1','2','3','4','5'],
                                    
                                    name: 'language_id'
                                        
                                }, {
                                    xtype: 'textfield',
                                    fieldLabel: 'Director Name',
                                    id: 'six',
                                    padding: 10,
                                    width: 470,
                                    name: 'director'
                                        
                                }, {
                                    xtype: 'textareafield',
                                    fieldLabel: 'Description',
                                    id: 'seven',
                                    padding: 10,
                                    hight: 50,
                                    width: 470,
                                    name: 'description'
                                       
                                }],
                                buttons: [{
                                    text: 'Save',
                                    margin: '0 10 0 0',
                                    handler: function () {
                                        Ext.Ajax.request({
                                            url: 'http://localhost:8080/Summer_Internship_Backend/addMovie.action', //Defined path of function defined in MVC 
                                            method: 'POST', 
                                            params: {
                                                Title: Ext.getCmp('one').value,
                                                Description: Ext.getCmp('seven').value,
                                                Director: Ext.getCmp('six').value,
                                                language_id: Ext.getCmp('five').value,
                                                Year: Ext.getCmp('two').value,
                                                Rating: Ext.getCmp('four').value,
                                                Special_Features: Ext.getCmp('three').value
                                            },
                                            success: function () {
                                                add_form.destroy();
                                                Ext.Msg.alert("Data Added Succesfully");

                                            },
                                            failure: function () {
                                                alert('fail');
                                            }

                                        });

                                    }
                                }, {
                                    text: 'Cancel',
                                    margin: '0 160 0 0',
                                    handler: function () {
                                        this.up('form').getForm().reset();
                                    }
                                }]
                            }
                            
                        }).show();
                    }
                }, {
                    xtype: 'button',
					tooltip:'Edit',
                    text: 'Edit',
					iconCls: 'x-fa fa-edit',
                    handler: function () {
                        var selectionModel = Ext.getCmp('Movie_grid').getSelectionModel();
                        var selectedRecords = selectionModel.getSelection();
                        var myvalue = selectedRecords[0];
                        var edit_form = Ext.create('Ext.window.Window', {
                            title: 'Edit Film',
                            height: 510,
                            width: 500,

                            items: {
                                xtype: 'form',
                                
                                id: 'edit_form',
                                
                                items: [{
                                    xtype: 'textfield',
                                    fieldLabel: 'Movie Name',
                                    padding: 10,
                                    width: 470,
                                    id: 'name',
                                    value: myvalue.get('title'),
                                    
                                    name: 'Title',
                                    
                                }, {
                                    xtype: 'numberfield',
                                    fieldLabel: 'Release Year',
                                    padding: 10,
                                    width: 470,
                                    id: 'ryear',
                                    value: myvalue.get('year'),
                                    name: 'Year',
                                    
                                }, {
                                    xtype: 'combobox',
                                    fieldLabel: 'Special Feature',
                                    padding: 10,
                                    width: 470,

                                    name: 'Special_Features',
                                    id: 'features',
                                    value: myvalue.get('special_Features'),
                                    store: ['Trailers', 'Commentaries', 'Deleted Scenes', 'Behind the Scenes'],
                                   
                                   
                                }, {
                                    xtype: 'combobox',
                                    fieldLabel: 'Rating',
                                    name: 'Rating',
                                    id: 'rate',
                                    value: myvalue.get('rating'),
                                    padding: 10,
                                    width: 470,
                                    store: ['G','PG','PG-13','R'],
                                   
                                   
                                }, {
                                    xtype: 'combobox',
                                    fieldLabel: 'Language',
                                    id: 'lang',
                                    name: 'language_id',
                                    value: myvalue.get('language_id'),
                                    padding: 10,
                                    width: 470,
                                    store: ['1','2','3','4','5'],

                                    
                                }, {
                                    xtype: 'textfield',

                                    fieldLabel: 'Director Name',
                                    id: 'dname',
                                    value: myvalue.get('director'),
                                    padding: 10,
                                    width: 470,
                                    name: 'Director'
                                        
                                }, {
                                    xtype: 'textareafield',
                                    fieldLabel: 'Description',
                                    id: 'desc',
                                    value: myvalue.get('description'),
                                    padding: 10,
                                    hight: 50,
                                    width: 470,
                                    name: 'Description',
                                   

                                }],
                                buttons: [{
                                    text: 'Save',
                                    margin: '0 10 0 0',
                                    handler: function () {
                                        Ext.getCmp('edit_form').submit({
                                            url: 'editMovie',
											params:{
												film_id:myvalue.get('film_id'),
											},
                                            success: function () {
												userStore.reload();
                                                edit_form.destroy();
                                                Ext.Msg.alert("Data Edit Succesfully");

                                            },
                                            failure: function () {
	                                          userStore.reload();
                                                edit_form.destroy();

                                             //   alert('fail');
                                            }
                                        })

                                    }
                                }, {
                                    text: 'Cancel',
                                    margin: '0 160 0 0',
                                    handler: function () {
                                        this.up('form').getForm().reset();
                                    }
                                }]
                            }
                            
                        }).show();
                    }

                }, {
                    xtype: 'button',
                    text: 'Delete',
					iconCls:'x-fa fa-trash-o',
					tooltip:'Delete',
                    handler: function () {
                        var selectionModel = Ext.getCmp('Movie_grid').getSelectionModel();
                        var selectedRecords = selectionModel.getSelection();
                        var myvalue = selectedRecords;
                        var arr=[];
						myvalue.forEach((value, index, array)=>{
							arr.push(value.get('film_id'));
						})
                        alert(arr);
                        Ext.Ajax.request({
                            url: 'http://localhost:8080/Summer_Internship_Backend/delMovie.action', //Defined path of function defined in MVC 
                            method: 'POST', 
                            params: {
                                film_id: arr,
                               
                            },
                            success: function () {
								userStore.reload();
                                Ext.Msg.alert("Deleted");

                            },
                            failure: function () {
                                alert('fail');
                            }

                        });

                    }
                }],
            }],

        });

    }
});
