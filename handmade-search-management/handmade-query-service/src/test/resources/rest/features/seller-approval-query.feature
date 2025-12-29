Feature: Seller Approval Queue Search Query Service

Scenario: Search all pending approvals
When I POST a REST request to URL "/q/search-seller-approvals" with payload
"""
{
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search approvals by current state
When I POST a REST request to URL "/q/search-seller-approvals" with payload
"""
{
	"filters" :{
		"currentState": "IDENTITY_VERIFICATION"
	},
	"sortCriteria" :[
		{"name":"createdAt","ascendingOrder": false}
	],
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search approvals by platform
When I POST a REST request to URL "/q/search-seller-approvals" with payload
"""
{
	"filters" :{
		"platformId": "PLATFORM_001"
	},
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search unverified approvals
When I POST a REST request to URL "/q/search-seller-approvals" with payload
"""
{
	"filters" :{
		"verified": false
	},
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search approvals by business name with LIKE query
When I POST a REST request to URL "/q/search-seller-approvals" with payload
"""
{
	"filters" :{
		"businessName": "Seller"
	},
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search approvals with multiple filters and sorting
When I POST a REST request to URL "/q/search-seller-approvals" with payload
"""
{
	"filters" :{
		"platformId": "PLATFORM_001",
		"currentState": "TAX_VERIFICATION",
		"kycCompleted": false
	},
	"sortCriteria" :[
		{"name":"createdAt","ascendingOrder": false}
	],
	"pageNum": 1,
	"numRowsInPage": 10
}
"""
Then the http status code is 200
And the top level code is 200
And success is true

Scenario: Search approvals with pagination
When I POST a REST request to URL "/q/search-seller-approvals" with payload
"""
{
	"sortCriteria" :[
		{"name":"createdAt","ascendingOrder": false}
	],
	"pageNum": 2,
	"numRowsInPage": 2
}
"""
Then the http status code is 200
And the top level code is 200
And success is true
And the REST response key "currentPage" is "2"
And the REST response key "numRowsInPage" is "2"

